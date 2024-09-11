import requests
from bs4 import BeautifulSoup
import csv





def clean_text(text):
    return ' '.join(text.split()).strip()


def writeDataIntoCSV(land_items):
    with open('land_details.csv', mode='w') as file:
        writer = csv.writer(file)
        writer.writerow(['Title', 'Image Link', 'Location', 'Publish Date', 'Total Price'])
        for item in land_items:
            writer.writerow([item['title'], item['img_link'], item['location'], item['publish_date'], item['total_price']])



def ScrapeLandDetails(content):
    soup = BeautifulSoup(content, 'html.parser')
    land_items = []

    # Finding all property cards using the correct class names
    land_cards = soup.find_all('div', class_='result-item result-item-property result-item-promoted col-10 col-sm-6 col-md-5 col-lg-12 mb-3')

    for card in land_cards:
        # Extract title
        title_tag = card.find('h4', class_='result-title')
        title = title_tag.text.strip() if title_tag else "No title"

        # Extract image link
        img_tag = card.find('img', class_='img-fluid lazy')
        img_link = img_tag['src'] if img_tag else "No image link"

        # Extract location
        location_tag = card.find('p', class_='clearfix result-agent mb-2')
        location = clean_text(location_tag.text) if location_tag else "No location"

        # Extract publish date
        date_tag = card.find('p', class_='clearfix mb-0')
        publish_date = clean_text(date_tag.text).replace('Create Date :', '').strip() if date_tag else "No publish date"

        # Extract total price
        total_price_tag = card.find('div', class_='result-payments price hidden-on-mobile')
        if total_price_tag:
            total_price = clean_text(total_price_tag.find('span', class_='money').text) if total_price_tag.find('span', class_='money') else "No total price"
        else:
            total_price = "No total price"

        # Add details to the list
        land_items.append({
            'title': title,
            'img_link': img_link,
            'location': location,
            'publish_date': publish_date,
            'total_price': total_price
        })

    writeDataIntoCSV(land_items)

# URL for the page to scrape
URL = 'https://www.patpat.lk/property?page=1'


for i in range(1, 6):
    URL = f'https://www.patpat.lk/property?page={i}'
    response = requests.get(URL)
    if (response.status_code == 200):
        print(f'Page {i} fetched successfully')
        ScrapeLandDetails(response.content)
        i += 1
    else:
        print("Failed to fetch the page")
        break

