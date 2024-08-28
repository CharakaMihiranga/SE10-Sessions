const path = require('path');

module.exports = {
  entry: {
    app: './js/auth.js',
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    clean: true,
    filename: './js/auth.js',
  },
};
