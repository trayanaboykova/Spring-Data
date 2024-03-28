package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskRootDto implements Serializable {
    @XmlElement(name = "task")
    private List<TaskSeedDto> tasks;

    public TaskRootDto() {
    }

    public List<TaskSeedDto> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskSeedDto> tasks) {
        this.tasks = tasks;
    }
}
