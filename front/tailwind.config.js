/** @type {import('tailwindcss').Config} */

module.exports = {
  content: [
    'node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}',
    "./src/**/*.{html,js,jsx,ts,tsx}"
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('flowbite/plugin') // 올바른 플러그인 경로로 수정
  ],
};