/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {
      backgroundImage: {
        'community': "url('../public/images/CommunityBackground.png')",
        'main': "url('../public/images/mainBackground.png')",
      },
      colors: {
        'kakao-bg' : '#FAE100',
        'kakao-text': '#3C1D1E',
        'dal-green' : '#315B4C'
      },
    },
  },
  plugins: [
    require('@tailwindcss/line-clamp'),
  ],
}
