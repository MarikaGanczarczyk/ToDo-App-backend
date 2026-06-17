function TaskItem({ task, onDelete, onComplete }) {
  return (
    <div
      className={`task-item ${
        task.status === "COMPLETED" ? "completed" : ""
      }`}
    >
      <h3>{task.title}</h3>

      <p>Status: {task.status}</p>
      <p >Priority: {task.priority}</p>

      {task.dueDate && <p>Due: {task.dueDate}</p>}

      <div className="task-buttons">
        {task.status !== "COMPLETED" && (
          <button
            className="complete"
            onClick={() => onComplete(task.id)}
          >
            Complete
          </button>
        )}

        <button
          className="delete"
          onClick={() => onDelete(task.id)}
        >
          Delete
        </button>
      </div>
    </div>
  );
}

export default TaskItem;