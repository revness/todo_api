
## Backend

## Key features
- Deleting a task should set an `isArchived` flag in the database instead of deleting the task from the database
- Add a filter to the frontend application that allows you to filter tasks by category
- Categories and Todos should be stored in separate tables

## Endpoints
- `GET /categories`
- `POST /categories`
- `PUT /categories/:id`
- `DELETE /categories/:id`

- `GET /todos`
- `GET /todos?category={}`
- `POST /todos`
- `PUT/PATCH /todos/:id`
- `DELETE /todos/:id`


## ChangeLog
- Working backend with todos/categories 2/9/24



## Todo
- Add unique feature to todo app. 
    -Set deadline date? we can use this to count down to this deadline, in the frontend.
    -Incentives/ Disincentives?
- Connect frontend to backend
- Create github actions integration.
- Work out how to containerise the app using docker and host on GCP/ AWS