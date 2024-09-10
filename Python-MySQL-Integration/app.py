from flask import Flask, render_template, request
from sqlalchemy import null

from entity.User import User
from model.UserModel import UserModel

app = Flask(__name__)


@app.route('/')
def home():  # put application's code here
    return render_template('index.html')

@app.route('/register', methods=['POST'])
def register():
    name = request.form.get('name')
    email = request.form.get('email')
    password = request.form.get('password')

    # Save the user details to the database
    user = User(
        name = name,
        email = email,
        password = User.hash_password(password)
    )
    return UserModel.save_user(user)

if __name__ == '__main__':
    app.run()
