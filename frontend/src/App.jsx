import { useState, useEffect } from 'react';
import {
  getAllTasks,
  addTask,
  updateTask,
  deleteTask,
  completeTask,
} from './api/taskApi';
import TaskForm from './components/TaskForm';
import TaskList from './components/TaskList';
import './App.css';






function App() {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [showForm, setShowForm] = useState(false);


  const [statusFilter, setStatusFilter] = useState('');
  const [priorityFilter, setPriorityFilter] = useState('');
   const [editingTask, setEditingTask] = useState(null);




  const loadTasks = async () => {
    setLoading(true);
    setError(null);
    try {
      const params = {};
      if (statusFilter) params.status = statusFilter;
      if (priorityFilter) params.priority = priorityFilter;

      const response = await getAllTasks(params);
      setTasks(response.data);
    } catch (err) {
      console.error(err);
      setError('Failed to load tasks. Check if the backend is running.');
    } finally {
      setLoading(false);
    }
  };

const handleEditClick = (task) => {
  setEditingTask(task);
  setShowForm(true);
};


  useEffect(() => {
    loadTasks();
  }, [statusFilter, priorityFilter]);

  const handleAddTask = async (newTask) => {
    try {

await addTask(newTask);
loadTasks();
setShowForm(false);

    } catch (err) {
      console.error(err);
      setError('Failed to load task.');
    }
  };

  const handleDeleteTask = async (id) => {
    try {
      await deleteTask(id);
      setTasks((prev) => prev.filter((task) => task.id !== id));
    } catch (err) {
      console.error(err);
      setError('Failed to delete task.');
    }
  };

  const handleCompleteTask = async (id) => {
    try {
      const response = await completeTask(id);
      setTasks((prev) =>
        prev.map((task) => (task.id === id ? response.data : task))

      );
    } catch (err) {
      console.error(err);
      setError('Copilot said: Failed to mark the task as completed. ');
    }


  };
const handleUpdateTask = async (taskData) => {
  try {
    const response = await updateTask(taskData.id, taskData);

    setTasks((prev) =>
      prev.map((task) =>
        task.id === taskData.id ? response.data : task
      )
    );

 setShowForm(false);
    setEditingTask(null);

  } catch (error) {
    console.error("Error updating task:", error);
    setError("Failed to update task");
  }
};
  return (
    <div className="app">
      <h1>Task Manager</h1>

<button onClick={() => setShowForm(true)}>
  Create New Task
</button>

    {showForm && (
      <TaskForm
        onAddTask={handleAddTask}
         onUpdateTask={handleUpdateTask}
    task={editingTask}

        onClose={() => {
       setShowForm(false);
      setEditingTask(null);
      }}

      />
    )}

      <div className="filters">
        <select
          value={statusFilter}
          onChange={(e) => setStatusFilter(e.target.value)}
        >
          <option value="">Status</option>
          <option value="PENDING">Pending</option>
          <option value="COMPLETED">Completed</option>
        </select>

        <select
          value={priorityFilter}
          onChange={(e) => setPriorityFilter(e.target.value)}
        >
          <option value="">Priority</option>
          <option value="LOW">Low</option>
          <option value="MEDIUM">Medium</option>
          <option value="HIGH">High</option>
        </select>
      </div>

      {error && <p className="error">{error}</p>}

      {loading ? (
        <p>Loading...</p>
      ) : (
        <TaskList
          tasks={tasks}
          onDelete={handleDeleteTask}
          onComplete={handleCompleteTask}
          onEdit={handleEditClick}

        />
      )}
    </div>
  );
}

export default App;
