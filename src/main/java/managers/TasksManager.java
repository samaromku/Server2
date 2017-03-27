package managers;


import entities.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksManager {
    Task task;
    List<Task> tasks;
    private String status;
    private String[] statusStrings = new String[]{"стандартная", "аварийная", "информационная", "периодическая"};
    private String[] type = new String[]{"к сведению", "приемка", "УУИТЭ", "ИТП", "АРТФ"};

    public String[] getType() {
        return type;
    }

    public String[] getStatusStrings() {
        return statusStrings;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Task getTask() {
        return task;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void addAll(List<Task> taskList){
        tasks.addAll(taskList);
    }

    public Task getById(int taskId){
        for(Task t:tasks){
            if(t.getId()==taskId){
                return t;
            }
        }
        return null;
    }

    public List<Task> getByUserId(int userId){
        List<Task>taskList = new ArrayList<>();
        for(Task t:tasks){
            if(t.getUserId()==userId){
                taskList.add(t);
            }
        }
        return taskList;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void updateTask(String status, int id){
        for(Task t:tasks){
            if(t.getId()==id){
                t.setStatus(status);
            }
        }
    }

    public void removeAll(){
        if(tasks.size()>0){
            tasks.clear();
        }
    }

    public int getMaxId(){
        int max = 0;
        for(Task t:tasks){
            if(t.getId()>max){
                max=t.getId();
            }
        }
        return max;
    }

    public TasksManager(){
        tasks = new ArrayList<>();
    }

    public void addUnique(List<Task>taskList){
        for(Task t:taskList){
            if(!tasks.contains(t)){
                tasks.add(t);
            }
        }
    }

    public void addUniqueTask(Task task){
            if(!tasks.contains(task)){
                tasks.add(task);
            }
    }
}

