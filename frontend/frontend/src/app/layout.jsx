import "./globals.css";

export const metadata = {
  title: "CineJoy",
  description: "Site do cinema CineJoy"
};

export default function RootLayout({ children }) {
  return (
    <html lang="pt-BR">
      <body>{children}</body>
    </html>
  );
}
