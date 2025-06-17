import React, { useState, useEffect, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import drugService from '../services/drugService';
import {
  Typography,
  Box,
  Card,
  CardContent,
  CardActions,
  Button,
  CircularProgress,
  Alert,
  Grid,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TablePagination
} from '@mui/material';
import { toast } from 'react-toastify';

const DrugListPage = () => {
  const navigate = useNavigate();
  const [drugs, setDrugs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [totalElements, setTotalElements] = useState(0);

  const fetchDrugs = useCallback(async (currentPage, currentRowsPerPage) => {
    setLoading(true);
    setError(null);
    try {
      const data = await drugService.getAllDrugs(currentPage, currentRowsPerPage, 'name', 'asc');
      if (data && Array.isArray(data.content)) {
        setDrugs(data.content);
        setTotalElements(data.totalElements || 0);
      } else {
        console.error("Unexpected data structure from getAllDrugs:", data);
        setDrugs([]);
        setTotalElements(0);
        setError('Received invalid data format for drugs list.');
      }
    } catch (err) {
      console.error('Error fetching drugs:', err);
      setError('Error fetching drugs: ' + (err.message || 'Unknown error'));
      setDrugs([]);
      setTotalElements(0);
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchDrugs(page, rowsPerPage);
  }, [fetchDrugs, page, rowsPerPage]);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleViewDetails = (drugId) => {
    navigate(`/drugs/${drugId}`);
  };

  const handleAddDrug = () => {
    navigate('/drugs/add');
  };

  if (loading) return <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}><CircularProgress /></Box>;
  if (error) return <Alert severity="error" sx={{ m: 2 }}>{error}</Alert>;

  return (
      <Box sx={{ maxWidth: 1000, mx: 'auto', p: 2 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
          <Typography variant="h4" gutterBottom>
            Drug List
          </Typography>
          <Button variant="contained" color="primary" onClick={handleAddDrug}>
            Add New Drug
          </Button>
        </Box>

        {drugs.length === 0 && !loading ? (
            <Alert severity="info">No drugs found.</Alert>
        ) : (
            <Paper sx={{ width: '100%', overflow: 'hidden' }}>
              <TableContainer>
                <Table stickyHeader aria-label="drug table">
                  <TableHead>
                    <TableRow>
                      <TableCell>Name</TableCell>
                      <TableCell>Manufacturer</TableCell>
                      <TableCell align="right">Price</TableCell>
                      <TableCell align="right">Quantity</TableCell>
                      <TableCell align="center">Actions</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {drugs.map((drug) => (
                        <TableRow hover role="checkbox" tabIndex={-1} key={drug.id}>
                          <TableCell component="th" scope="row">
                            {drug.name}
                          </TableCell>
                          <TableCell>{drug.manufacturer || 'N/A'}</TableCell>
                          <TableCell align="right">${drug.price != null ? drug.price.toFixed(2) : 'N/A'}</TableCell>
                          <TableCell align="right">{drug.quantity != null ? drug.quantity : 'N/A'}</TableCell>
                          <TableCell align="center">
                            <Button
                                variant="outlined"
                                size="small"
                                onClick={() => handleViewDetails(drug.id)}
                            >
                              View Details
                            </Button>
                          </TableCell>
                        </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
              <TablePagination
                  rowsPerPageOptions={[5, 10, 25, 50]}
                  component="div"
                  count={totalElements}
                  rowsPerPage={rowsPerPage}
                  page={page}
                  onPageChange={handleChangePage}
                  onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </Paper>
        )}
      </Box>
  );
};

export default DrugListPage;