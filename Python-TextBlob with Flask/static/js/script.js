   document.getElementById('review-form').addEventListener('submit', async function(event) {
        event.preventDefault();

        const reviewText = document.getElementById('review').value;

            const response = await fetch('/analyzereview', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ review: reviewText })
        });

        const data = await response.json();
        document.getElementById('result').textContent = 'Sentiment: ' + data.sentiment;
    });