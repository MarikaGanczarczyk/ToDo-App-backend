import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1/tasks';

// GET /api/v1/tasks
export const getAllTasks = (params = {}) => {
  return axios.get(API_URL, { params });
  // przykład użycia: getAllTasks({ priority: 'HIGH' })
  //                  getAllTasks({ status: 'PENDING' })
  //                  getAllTasks({ dueDate: '2026-06-20' })
};

// GET /api/v1/tasks/{id}
export const getTaskById = (id) => {
  return axios.get(`${API_URL}/${id}`);
};

// POST /api/v1/tasks
export const addTask = (task) => {
  return axios.post(API_URL, task);
};

// PUT /api/v1/tasks/{id}
export const updateTask = (id, updatedTask) => {
  return axios.put(`${API_URL}/${id}`, updatedTask);
};

// DELETE /api/v1/tasks/{id}
export const deleteTask = (id) => {
  return axios.delete(`${API_URL}/${id}`);
};

// PATCH /api/v1/tasks/{id}/complete
export const completeTask = (id) => {
  return axios.patch(`${API_URL}/${id}/complete`);
};

// GET /api/v1/tasks/search?keyword=...
export const searchTasksByTitle = (keyword) => {
  return axios.get(`${API_URL}/search`, { params: { keyword } });
};
