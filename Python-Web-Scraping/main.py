#import required libraries
import requests
from bs4 import BeautifulSoup

#url for the page to scrape
url = 'https://example.com/'

response = requests.get(url)


def scrapeData(content):
    # Parse the HTML content using BeautifulSoup
    soup = BeautifulSoup(content, 'html.parser')

    # Print the prettified content to view the structure
    print(soup.prettify());

    # Find the required elements using BeautifulSoup
    docTitle = soup.find('h1').text
    docParagraph = soup.find('p').text
    LinkText = soup.find('a').text
    LinkHref = soup.find('a')['href']
    print(f"Title: {docTitle}")
    print(f"Paragraph: {docParagraph}")
    print(f"Link text: {LinkText}")
    print(f"Link href: {LinkHref}")

    # Alternatively, you can use the .string attribute to get the text content
    print("\n===============Using .string attribute===============")

    docTitle = soup.h1.string
    docParagraph = soup.p.string
    LinkText = soup.a.string
    LinkHref = soup.a['href']

    print(f"Title: {docTitle}")
    print(f"Paragraph: {docParagraph}")
    print(f"Link text: {LinkText}")
    print(f"Link href: {LinkHref}")


def scrapeMultipleData(content):
    # Parse the HTML content using BeautifulSoup
    soup = BeautifulSoup(content, 'html.parser')

    # Find all the required elements using BeautifulSoup
    tags = soup.find_all(['h1', 'p'])
    for tag in tags:
        print(tag.text.strip())


if (response.status_code == 200):
    print('Page fetched successfully')
    # print(response.text)
    # scrapeData(response.content)
    scrapeMultipleData(response.content)
else:
    print("Failed to fetch the page")

