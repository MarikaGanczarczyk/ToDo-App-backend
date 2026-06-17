import TaskItem from "./TaskItem";

function TaskList({ tasks, onDelete, onComplete }) {
  if (tasks.length === 0) {
    return <p>No tasks found</p>;
  }

  return (
    <div className="task-list">
      {tasks.map((task) => (
        <TaskItem
          key={task.id}
          task={task}
          onDelete={onDelete}
          onComplete={onComplete}
        />
      ))}
    </div>
  );
}

export default TaskList;