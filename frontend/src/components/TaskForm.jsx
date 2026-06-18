import { useState, useEffect } from "react";

function TaskForm({ onAddTask, onClose, onUpdateTask, task }) {
  const [title, setTitle] = useState("");
  const [priority, setPriority] = useState("LOW");
  const [description, setDescription] = useState("");
  const [dueDate, setDueDate] = useState(
    new Date().toISOString().split("T")[0]
  );

  // ✅ wypełnianie przy edycji
  useEffect(() => {
    if (task) {
      setTitle(task.title || "");
      setDescription(task.description || "");
      setPriority(task.priority || "LOW");
      setDueDate(task.dueDate || "");
    }
  }, [task]);

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!title.trim()) {
      return alert("Title cannot be empty");
    }

    const newTask = {
      title,
      description,
      priority,
      dueDate,
      status: task?.status || "PENDING"
    };

    if (task) {
      // ✅ EDIT
       onUpdateTask({ id: task.id, ...newTask });
    } else {
      // ✅ ADD
      onAddTask(newTask);
    }

    // reset
    setTitle("");
    setDescription("");
    setPriority("LOW");
    setDueDate("");
  };

  return (
    <form onSubmit={handleSubmit}>
      <h3>{task ? "Edit Task" : "Add Task"}</h3>

      <input
        type="text"
        placeholder="Task title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />

      <input
        type="text"
        placeholder="Description"
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

      <input
        type="date"
        value={dueDate}
        onChange={(e) => setDueDate(e.target.value)}
      />

      <div className="buttons">
        <button type="submit">
          {task ? "Update Task" : "Add Task"}
        </button>

        <button type="button" onClick={onClose}>
          Cancel
        </button>
      </div>
    </form>
  );
}

export default TaskForm;