package exercise.mapper;

import exercise.dto.*;
import exercise.model.Task;
import org.mapstruct.*;

@Mapper(
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    // BEGIN
    @Mapping(target = "assignee.id", source = "assigneeId")
    public abstract Task map(TaskCreateDTO dto);

    public abstract Task update(TaskUpdateDTO taskDto, @MappingTarget Task task);

    @Mapping(target = "assigneeId", source = "assignee.id")
    public abstract TaskDTO map(Task model);
    // END

}
