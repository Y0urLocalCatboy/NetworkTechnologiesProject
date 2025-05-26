import React, { useState, useEffect } from 'react';
import { 
  Typography, 
  Box, 
  Paper, 
  Button, 
  Alert
} from '@mui/material';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';

const Profile = () => {
  const { logout } = useAuth();
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await axios.get('/api/users/me');
        setProfile(response.data);
      } catch (err) {
        setError(err.toString);
      }
    };

    fetchProfile();
  }, []);

  if (!profile && !error) {
    return <Box>Loading...</Box>;
  }

  return (
    <Box>
      <Typography variant="h5" gutterBottom>
        My Profile
      </Typography>

      {error && <Alert severity="error">{error}</Alert>}

      {profile && (
        <Paper sx={{ p: 3, mt: 2 }}>
          <Typography variant="h6">
            {profile.name} {profile.surname}
          </Typography>

          <Typography>
            Email: {profile.email}
          </Typography>

          <Button 
            variant="contained" 
            sx={{ mt: 3 }}
            onClick={() => {
              logout();
              window.location.href = '/login';
            }}
          >
            Logout
          </Button>
        </Paper>
      )}
    </Box>
  );
};

export default Profile;
