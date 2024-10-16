# My Personal Project -- PlanWise: Personal Plan Maker

## Project Description 
### "Wise Plan for Wise Minds!!"
The PlanWise application will help users **create personal plans** for their daily study or work tasks. Users can add their assignments to the *Assignments List*, visualizing the tasks they plan to accomplish each day. Additionally, the application allows users to record the *expected study/work time* and the *actual time spent* on each task, as well as whether the task is *completed*. This helps users keep track of their time management and the completeness of their assignments.

The **target users** of PlanWise are mostly students and office workers who regularly need to handle works with strict deadline, such as projects and written assignments. The **reason** why I decided to do this project because of the social phenomenon I observed. Procrastination is a common challenge in today's fast-paced world, people always like to delay tasks until the last minute. I have similar experience before, often feeling anxious and inefficient as deadlines approach. However, I discovered that creating a daily plan and evenly distributing tasks over time significantly improves my productivity and reduces stress. This gradual approach to managing tasks makes me feel more in control, relaxed, and efficient.By visualizing a personal plan, recording daily work hours, and tracking completed and pending tasks, we can greatly enhance our productivity. This method not only helps in meeting deadlines more comfortably but also promotes a healthier and more balanced approach to work and study.

## User Stories
- As a user, I want to be able to add an assignment to my daily plan.
- As a user, I want to be able to view the list of assignments in my plan.
- As a user, I want to be able to record an assignment's actual time spent and whether it is completed in my plan.
- As a user, I want to be able to remove an assignment from my plan.
- As a user, I want to see statistics comparing the time I actually spent versus my expected time today, as well as the assignments I have not completed.
- As a user, when I select the quit option from the application menu, I want to be reminded to save my changes to file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load my plan from file.

## Instructions for Grader
- You can generate the first required action related to the user story "add an assignment to my daily plan" by first clicking the "Add Assignment" button, typing the assignment's name, description, and expected completion time, and then clicking the "OK" button to complete the adding process.
- You can generate the second required action related to the user story "remove an assignment from my plan" by first clicking the "Remove Assignment" button, typing the name of the assignment you would like to remove, and then clicking the "OK" button to complete the removing process.
- You can generate the third required action related to the user story "view the list of assignments in my plan" by pressing the "View Your Plan" button. It will display all of the assignments you have so far in your plan and their details.
- You can generate the fourth required action related to the user story "record assignment details" by first pressing the "Record Progress" button. There will be pop-up windows in order of the assignment being added for each assignment. You can record the actual time spent on the assignment and mark the assignment as completed by choosing the checkbox.
- You can generate the fifth required action related to the user story "see statistics" by clicking the "Get Report" button. A paragraph will pop up with the information comparing the time you actually spent versus your expected time today, as well as the assignments you have not completed.
- You can locate my visual component on the front page of the application.
- You can save the state of my application by clicking "Yes" when the pop-up window "Save Warning" appears on the screen.
- You can reload the state of my application by clicking "Yes" when the pop-up window "Load Plan" appears on the screen.

## Phase 4: Task 2
### Sample
Tue Aug 06 17:33:15 PDT 2024
Plan Loaded

Tue Aug 06 17:33:15 PDT 2024
Actual Time Updated: WRDS150

Tue Aug 06 17:33:15 PDT 2024
Marked as Incompleted: WRDS150

Tue Aug 06 17:33:15 PDT 2024
Added Assignment: WRDS150

Tue Aug 06 17:33:25 PDT 2024
Added Assignment: CPSC210

Tue Aug 06 17:34:01 PDT 2024
Added Assignment: PHIL220

Tue Aug 06 17:34:07 PDT 2024
Removed Assignment: PHIL220

Tue Aug 06 17:34:08 PDT 2024
Plan Viewed

Tue Aug 06 17:34:08 PDT 2024
Plan viewed

Tue Aug 06 17:34:08 PDT 2024
Plan viewed

Tue Aug 06 17:34:16 PDT 2024
Plan Viewed

Tue Aug 06 17:34:23 PDT 2024
Actual Time Updated: WRDS150

Tue Aug 06 17:34:23 PDT 2024
Marked as Completed: WRDS150

Tue Aug 06 17:34:30 PDT 2024
Actual Time Updated: CPSC210

Tue Aug 06 17:34:30 PDT 2024
Marked as Incompleted: CPSC210

Tue Aug 06 17:34:31 PDT 2024
Plan Viewed

Tue Aug 06 17:34:31 PDT 2024
Report Viewd

Tue Aug 06 17:34:36 PDT 2024
Plan Saved

## Phase 4: Task 3
Based on the UML Diagram, I think I can refactor the project by reducing duplicate code. For example, in the DailyPlanGUI, where I added five buttons into the main panel, the code inside the setupTopButton and setupBottomButton are highly duplicated. To improve that, I can try to write some helper methods to make the code more readable and easier to debug.
Also, if I have more time to do this project, I would like to improve my viewPlan method in the DailyPlanGUI class. My current viewPlan method illustrates the list of assignments in a rough way which does not look pretty. If possible, i would like to make a JTable for the list so that all the details would be illustrate in a organized chart.
