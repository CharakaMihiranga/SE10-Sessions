import requests
from bs4 import BeautifulSoup
import csv

#url for the page to scrape
URL = 'https://www.geeksforgeeks.org/fundamentals-of-algorithms/'

response = requests.get(URL)

#function to scrape titles and contents
def writeDataIntoCSV(titles, contents):
    with open('title_and_contents.csv', mode='w') as file:
        writer = csv.writer(file)
        writer.writerow(['Title', 'Content'])
        for title, content in zip(titles, contents):
            writer.writerow([title.text, content.text])
        print("Data written to csv file successfully")


def downloadImages(imgLinks):
    for img in imgLinks:
        try:
            imgSrc = img['src']
            imgName = imgSrc.split('/')[-1]
            imgData = requests.get(imgSrc)
            imgData.raise_for_status()  # Check if the request was successful
            with open(imgName, 'wb') as file:
                file.write(imgData.content)
            print(f"Image {imgName} downloaded successfully")
        except Exception as e:
            print(f"Failed to download image {imgName}. Error: {e}")



def ScrapeTitlesAndContents(content):
    soup = BeautifulSoup(content, 'html.parser')
    # Find all the required elements using BeautifulSoup
    titles = soup.find_all('h2')
    contents = soup.find_all('p')
    imgLinks = soup.find_all('img')
    writeDataIntoCSV(titles, contents)
    downloadImages(imgLinks)



if (response.status_code == 200):
    ScrapeTitlesAndContents(response.content)
else:
    print("Failed to fetch the page")


