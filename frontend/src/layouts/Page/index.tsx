import Header from 'components/Header';

interface PageProps {
  children: React.ReactNode;
}

function Page({ children }: PageProps) {
  return (
    <>
      <Header />
      {children}
    </>
  );
}

export default Page;
