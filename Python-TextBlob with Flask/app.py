from flask import Flask, request, render_template, jsonify
from textblob import TextBlob


app = Flask(__name__)


@app.route('/')
def home():
    return render_template('index.html')

@app.route('/analyzereview', methods=['POST'])
def analyzereview():
    data = request.get_json()
    review = data.get('review')
    blob = TextBlob(review)
    sentiment = blob.sentiment.polarity

    if sentiment > 0:
        result = "Positive Review✅"
    elif sentiment < 0:
        result = "Negative Review☹️"
    elif sentiment == 0:
        result = "Neutral Review😐"

    return jsonify({'sentiment': result})


if __name__ == '__main__':
    app.run()
