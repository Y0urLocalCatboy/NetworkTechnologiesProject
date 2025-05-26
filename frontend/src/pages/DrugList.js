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
import axios from 'axios';

const DrugList = () => {
  const [drugs, setDrugs] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchDrugs = async () => {
      try {
        const response = await axios.get('/api/drugs?page=0&size=12');
        setDrugs(response.data.content || []);
      } catch (err) {
        setError(err.toString);
      }
    };

    fetchDrugs();
  }, []);

  return (
    <Box>
      <Typography variant="h5" gutterBottom>
        Available Drugs
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
    </Box>
  );
};

export default DrugList;
