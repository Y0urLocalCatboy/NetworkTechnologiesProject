import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';

import 'react-toastify/dist/ReactToastify.css';

import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Profile from './pages/Profile';
import DrugList from './pages/DrugList';
import DrugDetail from './pages/DrugDetail';
import AddDrug from './components/AddDrug';
import NotFound from './pages/NotFound';

import Layout from './components/Layout';

import { AuthProvider, useAuth } from './context/AuthContext';

const theme = createTheme({
  palette: {
    primary: {
      main: '#2196f3',
    },
    secondary: {
      main: '#f50057',
    },
  },
});

const ProtectedRoute = ({ children }) => {
  const { isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" />;
  }

  return children;
};

function App() {
  return (
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <AuthProvider>
          {/* Jeśli ToastContainer nie jest w Layout, powinien być tutaj */}
          {/* <ToastContainer /> */}
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/" element={
              <ProtectedRoute>
                <Layout />
              </ProtectedRoute>
            }>
              <Route index element={<Dashboard />} />
              <Route path="profile" element={<Profile />} />
              <Route path="drugs" element={<DrugList />} />
              <Route path="drugs/add" element={<AddDrug />} /> {/* Nowa trasa do dodawania leku */}
              <Route path="drugs/:id" element={<DrugDetail />} />
            </Route>
            <Route path="*" element={<NotFound />} />
          </Routes>
        </AuthProvider>
      </ThemeProvider>
  );
}

export default App;