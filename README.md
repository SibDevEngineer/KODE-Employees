# KODE Employees

Демо-приложение для просмотра списка сотрудников компании, а также детальной информации о выбранном сотруднике.

Приложение написано согласно [тестового задания](https://github.com/appKODE/trainee-test-android).

## Stack

- Kotlin
- MVVM
- Retrofit, OkHTTP
- Moshi
- Coroutines
- Adapter Delegates
- Dagger
- Unit и UI тесты

## Функционал приложения

- Фильтр сотрудников по департаментам.
- Поиск сотрудников по имени, фамилии, тэгу или номеру телефона.
- Сортировка сотрудников по алфавиту или дате рождения.
- Переход в профиль и просмотр детальной информации о сотруднике.

<p align="center">
<img src="/preview/demo/filter_users.gif" width="200"/>
<img src="/preview/demo/search_users.gif" width="200"/>
<img src="/preview/demo/sort_users.gif" width="200"/>
<img src="/preview/demo/user_profile.gif" width="200"/>
</p>

## UI States

<p align="center">
<img src="/preview/screenshots/state_loading.png" width="200"/>
<img src="/preview/screenshots/state_empty_list.png" width="200"/>
<img src="/preview/screenshots/state_error.png" width="200"/>
<img src="/preview/screenshots/state_user_not_found.png" width="200"/>
</p>

## Architecture

<p align="center">
<img src="/preview/app_architecture.png"/>
</p>
