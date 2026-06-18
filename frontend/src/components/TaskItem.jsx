function TaskItem({ task, onDelete, onComplete, onEdit }) {





  return (
    <div
      className={`task-item ${
        task.status === "COMPLETED" ? "completed" : ""
      }`}
    >
      {/* TITLE */}
      <h3>{task.title}</h3>

      {/* DESCRIPTION */}
      {task.description && (
        <p className="task-desc">{task.description}</p>
      )}

      {/* META INFO (status + priority + due) */}
      <div className="task-meta">
        <span className={`badge ${(task.status || "").toLowerCase()}`}>
          {task.status}
        </span>

        <span className={`priority ${(task.priority || "").toLowerCase()}`}>
          {task.priority}
        </span>

        {task.dueDate && (
          <span className="due-date">Due: {task.dueDate}</span>
        )}
      </div>

      {/* BUTTONS */}
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
        <button type="button" className="edit"
                  onClick={() => onEdit(task)}>Edit

            </button>
      </div>
    </div>
  );
}

export default TaskItem;