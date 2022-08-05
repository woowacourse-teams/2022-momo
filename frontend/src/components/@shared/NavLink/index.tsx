import { useTheme } from '@emotion/react';
import { NavLink as RouterLink } from 'react-router-dom';

interface LinkProps {
  to: string;
  children: React.ReactNode;
}

function NavLink({ to, children }: LinkProps) {
  const theme = useTheme();
  const style = ({ isActive }: { isActive: boolean }) => ({
    color: isActive ? theme.colors.yellow002 : theme.colors.black002,
  });

  return (
    <RouterLink to={to} style={style}>
      {children}
    </RouterLink>
  );
}

export default NavLink;
