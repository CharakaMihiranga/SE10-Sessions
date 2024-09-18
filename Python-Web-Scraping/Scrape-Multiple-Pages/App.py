import requests
from bs4 import BeautifulSoup
import csv


# Function to clean text by removing extra whitespace
def clean_text(text):
    return ' '.join(text.split()).strip()


# Function to write land details into a CSV file
def writeDataIntoCSV(land_items):
    with open('land_details.csv', mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(['Title', 'Image Link', 'Location', 'Publish Date', 'Total Price'])
        for item in land_items:
            writer.writerow([item['title'], item['img_link'], item['location'], item['publish_date'], item['total_price']])


# Function to scrape and extract land details from the page content
def ScrapeLandDetails(content):
    soup = BeautifulSoup(content, 'html.parser')
    land_items = []

    # Find all property cards using the correct class names
    land_cards = soup.find_all('div', class_='result-item')

    if not land_cards:
        # If no land cards are found, return False to stop scraping
        return False

    for card in land_cards:
        # Extract title
        title_tag = card.find('h4', class_='result-title')
        title = title_tag.text.strip() if title_tag else "No title"

        # Extract image link
        img_tag = card.find('img', class_='img-fluid')
        img_link = img_tag['src'] if img_tag else "No image link"

        # Extract location
        location_tag = card.find('span', class_='d-block')
        location = clean_text(location_tag.text) if location_tag else "No location"

        # Extract publish date
        date_tag = card.select_one('p.result-agent + p span.d-block')
        publish_date = clean_text(date_tag.text).replace('Create Date :', '').strip() if date_tag else "No publish date"

        # Extract total price
        total_price_tag = card.find('div', class_='result-payments price')
        total_price = clean_text(total_price_tag.find('span', class_='money').text) if total_price_tag and total_price_tag.find('span', class_='money') else "No total price"

        # Append land details to the list
        land_items.append({
            'title': title,
            'img_link': img_link,
            'location': location,
            'publish_date': publish_date,
            'total_price': total_price
        })

    # Write the extracted data to the CSV file
    writeDataIntoCSV(land_items)
    return True


# URL for the page to scrape
base_url = 'https://www.patpat.lk/property?page={}'

# Start scraping from page 1 and continue until no more listings are found
page_no = 1
while True:
    url = base_url.format(page_no)
    response = requests.get(url)

    if response.status_code == 200:
        print(f'Page {page_no} fetched successfully')
        if not ScrapeLandDetails(response.content):
            print(f"No more listings found on page {page_no}. Stopping the scraper.")
            break
        page_no += 1
    else:
        print(f"Failed to fetch page {page_no}")
        break

print("Scraping complete.")
