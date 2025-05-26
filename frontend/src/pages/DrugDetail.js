import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import { 
  Typography, 
  Box, 
  Paper, 
  Button, 
  Alert
} from '@mui/material';
import axios from 'axios';

const DrugDetail = () => {
  const { id } = useParams();
  const [drug, setDrug] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchDrug = async () => {
      try {
        const response = await axios.get(`/api/drugs/${id}`);
        setDrug(response.data);
      } catch (err) {
        setError(err.toString);
      }
    };

    fetchDrug();
  }, [id]);

  if (!drug && !error) {
    return <Box>Loading...</Box>;
  }

  if (error) {
    return (
      <Box>
        <Alert severity="error">{error}</Alert>
        <Button component={Link} to="/drugs">
          Back to Drugs
        </Button>
      </Box>
    );
  }

  return (
    <Box>
      <Button component={Link} to="/drugs" sx={{ mb: 2 }}>
        Back to Drugs
      </Button>

      <Paper sx={{ p: 3 }}>
        <Typography variant="h5" gutterBottom>
          {drug.name}
        </Typography>

        <Typography variant="body1" paragraph>
          {drug.description || 'No description available.'}
        </Typography>

        <Typography variant="h6">
          ${drug.price}
        </Typography>

        <Typography>
          In Stock: {drug.quantity || 0}
        </Typography>

        <Button 
          variant="contained" 
          sx={{ mt: 2 }}
          disabled={!drug.quantity || drug.quantity <= 0}
        >
          Add to Cart
        </Button>
      </Paper>
    </Box>
  );
};

export default DrugDetail;
