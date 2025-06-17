import React, { useState, useEffect } from 'react';
import { 
  Typography, 
  Box, 
  Grid, 
  Card, 
  CardContent, 
  Button,
  Alert
} from '@mui/material';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import axios from 'axios';
import drugService from '../services/drugService';


const Dashboard = () => {
  const { user } = useAuth();
  const [drugs, setDrugs] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchDrugs = async () => {
      try {
        const data = await drugService.getAllDrugs(0, 4);
        setDrugs(data.content || []);
        setError('');
      } catch (err) {
        console.error('Error fetching drugs for dashboard:', err);
        setError(err.message || 'Failed to fetch drugs');
      }
    };

    fetchDrugs();
  }, []);

  return (
    <Box>
      <Typography variant="h5" gutterBottom>
        Welcome, {user?.name || 'User'}
      </Typography>

      <Typography variant="h6" gutterBottom>
        Featured Drugs
      </Typography>

      {error ? (
        <Alert severity="error">{error}</Alert>
      ) : (
        <Grid container spacing={2}>
          {drugs.map((drug) => (
            <Grid item xs={12} sm={6} key={drug.id}>
              <Card>
                <CardContent>
                  <Typography variant="h6">
                    {drug.name}
                  </Typography>
                  <Typography>
                    ${drug.price}
                  </Typography>
                  <Button component={Link} to={`/drugs/${drug.id}`}>
                    View Details
                  </Button>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}

      <Box sx={{ mt: 3 }}>
        <Button 
          variant="contained" 
          component={Link} 
          to="/drugs"
          sx={{ mr: 2 }}
        >
          All Drugs
        </Button>
        <Button 
          variant="outlined" 
          component={Link} 
          to="/profile"
        >
          Profile
        </Button>
      </Box>
    </Box>
  );
};

export default Dashboard;
