document.getElementById('review-form').addEventListener('submit', async function(event) {
    event.preventDefault();

    const reviewText = document.getElementById('review').value;

    try {
        const response = await fetch('/analyzereview', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ review: reviewText })
        });

        const data = await response.json();
        document.getElementById('result').textContent = 'Sentiment: ' + data.sentiment;

        // document.getElementById('review').value = '';
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('result').textContent = 'An error occurred. Please try again.';
    }
});
