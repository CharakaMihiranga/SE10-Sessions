from textblob import TextBlob
# import nltk
# nltk.download('all') // to download natural language toolkit

testimonial = TextBlob("Textblob is amazingly simple to use. What great fun!")


if __name__ == '__main__':
    print(testimonial.sentiment)
    print(testimonial.sentiment.polarity)


