import { useState } from "react";

function TaskForm({ onAddTask, onClose }) {
  const [title, setTitle] = useState("");
  const [priority, setPriority] = useState("LOW");
  const [description, setDescription] = useState("")
  const [status, setStatus] = useState("COMPLETED")
  const [dueDate, setDueDate] = useState(new Date().toISOString().split("T")[0])

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!title.trim()) {
      return alert("Title cannot be empty");
    }

    onAddTask({
      title,
      priority,
      description,
      status,
      dueDate
    });

    setTitle("");
    setPriority("LOW");
    setDescription("");
    setStatus("COMPLETED")
    setDueDate(new Date().toISOString().split("T")[0])
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>Add Task</h3>

      <input
        type="text"
        placeholder="Task title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <input
              type="text"
              placeholder="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />

      <select
        value={priority}
        onChange={(e) => setPriority(e.target.value)}
      >
        <option value="LOW">Low</option>
        <option value="MEDIUM">Medium</option>
        <option value="HIGH">High</option>
      </select>
       <select
              value={status}
              onChange={(e) => setStatus(e.target.value)}
            >

              <option value="PENDING">Pending</option>
              <option value="COMPLETED">Completed</option>

            </select>

<input
  type="date"
  value={dueDate}
  onChange={(e) => setDueDate(e.target.value)}
/>

<div className="buttons">
     <button type="submit">Add Task</button>
     <button type="button" onClick={onClose}>
       Cancel
     </button>
     </div>
    </form>
  );
}

export default TaskForm;