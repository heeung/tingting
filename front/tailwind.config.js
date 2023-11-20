/** @type {import('tailwindcss').Config} */

import flowbitePlugin from 'flowbite/plugin';

const tailwindConfig = {
  content: [
    "./src/**/*.{html,js,jsx,ts,tsx}",
    'node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}'
  ],
  theme: {
    extend: {},
  },
  plugins: [
    flowbitePlugin,
  ],
};

export default tailwindConfig;