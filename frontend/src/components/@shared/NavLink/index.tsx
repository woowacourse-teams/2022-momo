import { NavLink as RouterLink } from 'react-router-dom';

import theme from 'styles/theme';

interface LinkProps {
  to: string;
  children: React.ReactNode;
}

function NavLink({ to, children }: LinkProps) {
  const activeStyle = {
    color: theme.colors.yellow002,
  };

  const defaultStyle = {
    color: theme.colors.white001,
  };

  return (
    <RouterLink
      to={to}
      style={({ isActive }) => (isActive ? activeStyle : defaultStyle)}
    >
      {children}
    </RouterLink>
  );
}

export default NavLink;
