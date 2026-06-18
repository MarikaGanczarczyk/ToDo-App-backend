import TaskItem from "./TaskItem";

function TaskList({ tasks, onDelete, onComplete }) {
 if (tasks.length === 0) {
   return (
     <div className="empty-state">
       <p>📝 No tasks yet</p>
       <span>Start by creating your first task</span>
     </div>
   );
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