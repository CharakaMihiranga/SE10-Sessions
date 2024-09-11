#import required libraries
import requests
from bs4 import BeautifulSoup

#url for the page to scrape
url = 'https://example.com/'

response = requests.get(url)


def scrapeData(content):
    soup = BeautifulSoup(content, 'html.parser')
    # Find the required elements using BeautifulSoup
    docH1Value = soup.find('h1').text
    docPtagValue = soup.find('p').text
    aTagValue = soup.find('a').text
    aTagHref = soup.find('a')['href']
    print(f"Title: {docH1Value}")
    print(f"First paragraph: {docPtagValue}")
    print(f"Link text: {aTagValue}")
    print(f"Link href: {aTagHref}")


if (response.status_code == 200):
    print('Page fetched successfully')
    print(response.text)
    scrapeData(response.content)
else:
    print("Failed to fetch the page")

