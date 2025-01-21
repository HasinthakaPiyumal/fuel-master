import PropTypes from 'prop-types';

const AdminLayout = ({ children }) => {
  return (
    <div className="app-layout">
      <main className="main-content">
        {children}
      </main>
    </div>
  );
};

AdminLayout.propTypes = {
  children: PropTypes.node.isRequired,
};

export default AdminLayout;
