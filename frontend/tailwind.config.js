/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {
      backgroundImage: {
        'community': "url('../public/images/CommunityBackground.png')",
      },
    },
  },
  plugins: [
    require('@tailwindcss/line-clamp'),
  ],
}
