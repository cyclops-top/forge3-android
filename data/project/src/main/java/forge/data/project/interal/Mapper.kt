package forge.data.project.interal

import forge.data.project.local.ProjectLocal
import forge.data.project.remote.ProjectRemote
import forge.model.Project

internal fun ProjectRemote.toProject():Project{
    return Project(
        id = id,
        name = name,
        description = description,
        `package` = `package`,
        sign = sign,
        icon = icon,
        private = private,
        createdTime = createdTime,
        lastVersion = lastVersion,
        isCollected = isCollected ?: false
    )
}

internal fun ProjectRemote.toLocal():ProjectLocal{
    return ProjectLocal(
        id = id,
        name = name,
        description = description,
        `package` = `package`,
        sign = sign,
        icon = icon,
        private = private,
        createdTime = createdTime,
        createdBy = createdBy,
        updateTime = updateTime,
        lastVersion = lastVersion,
        isCollected = isCollected
    )
}

internal fun ProjectLocal.toProject():Project{
    return Project(
        id = id,
        name = name,
        description = description,
        `package` = `package`,
        sign = sign,
        icon = icon,
        private = private,
        createdTime = createdTime,
        lastVersion = lastVersion,
        isCollected = isCollected ?: false
    )
}
