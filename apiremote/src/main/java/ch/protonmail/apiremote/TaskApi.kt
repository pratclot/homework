package ch.protonmail.apiremote

import ch.protonmail.data.TaskDTO
import retrofit2.http.GET
import retrofit2.http.Url

private const val DEFAULT_TASK_PATH = "https://proton-android-testcloud.europe-west1.firebasedatabase.app/tasks.json"

interface TaskApi {

    @GET
    suspend fun fetch(@Url path: String = DEFAULT_TASK_PATH): List<TaskDTO>
}
