import ReactDOM from 'react-dom/client';
import App from './App';
import './index.css';
import apiService from './services/api.service';

apiService.init();

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
