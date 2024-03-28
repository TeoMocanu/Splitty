| Key          | Value                                                                   |
| ------------ | ----------------------------------------------------------------------- |
| Date:        | 27/02/2024                                                              |
| Time:        | 16:45 - 17:45                                                           |
| Location:    | Drebbleweg                                                              |
| Chair        | Matej Kliment                                                           |
| Minute Taker | Mario Nicolae                                                           |
| Attendees:   | Mario Nicolae <br/>Mihai Nicolae<br/>Matei Dumitrescu<br/>Matej Kliment<br/>Teodor Mocanu</br>Sebastian Mustață |

Agenda Items:

- Opening by chair (1 min)
- Approval of the agenda - Does anyone have any additions? (1 min)
- Check-in:
    - How is everyone doing? (2 min)
        - Everyone is doing great! We all passed the Git assignment!
    - Code of Conduct assignment checkup (2 min)
        - Are we all done with our parts?
            - Everyone wrote and pasted their part on the Overleef document.
        - Introduction and conclusion
            - We will have a special meeting on Thursday to write the introduction and conclusion for the document, as well as to check for any mistakes.
        - When can we have a final editing/proofreading session? (online?)
            - We will have a meeting on Friday on our discord server to add the finishing touches to the document.
    - Progress checkup: Can we all summarize what has been done in the past week? (2 min)
        - Everybody attended the lectures and did the exercises.
        - Did any problems occur?
            - There were no problems. Everybody did the 100+ lines of code, 3+ commits, 1+ merge request and 1+ comment on someone else's merge request
            - Everybody created at least one class, which we are going to use for the UI pages and tested them.
- Talking Points: (Inform/ brainstorm/ decision making/ discuss)
    - Planning of the next steps (15 min)
        - What are the most important parts to implement right now?
            - We decided that a crucial part to think of and implement is about the database. Also, on Thursday, we are going to make a perfect conceptual schema for the database and on the meeting from Friday, some of us are going to implement it. Besides the database, we have to work on the UI pages and code the controllers.
        - how do we want to distribute the work?
            - 2 of us are going to work on the database and a bit on the UI and the rest are going to work on the UI and the controllers.
    - Create issues and milestones (10 min)
        - We created a Milestone Basic Features, where we have 30 issues in 3 categories: Technical, Scene and User Story. Our plan is to finish the Technical ones and the Scene ones this week!
    - Structure of the application (10 min)
        - What objects do we keep (which ones are necessary)?
            - We are going to keep only Event, Expense, Participant and Debt.
        - What information should be stored in which of the objects?
            - In Event, we are going to store id + name, in Participant id + name + iban + BIC + email.
            - In Debt, we are going to store event_id + amount, in Expense event_id + tag + amount + currency + date.
        - What window/scene should have what functionality - which screen leeds to which window
            - From Start Screen, we could go to the Overview of an event and Language Selection.
            - From an Overview, we could go to Invitation, Add Expense, Open Debts and Statistics.
            - From Add Expense, we could go to Contact Details.
    - Discuss next steps: Planning + Splitting tasks (8 min)
        - what can we achieve by next Tuesday?
            - We will have a working database and the implementation of the pages of our application.
- Summarize action points: Who , what , when? (2 min)
    - Mihai is going to finish the Starter Page(UI + Controller) until Friday, so that in the weekend we can make the connections between the Starter Page and the other pages.
    - Sebastian and Teodor are going to work until Saturday on the database.
    - I am going to finish until Saturday the Invitation Scene and Open Debts per Event.
    - Matei is going to finish until Sunday Admin login page + Admin screens.
    - Sebastian is going to finish until Sunday Add/Edit Participant interface.
    - Teodor is going to make the Event Overview.
    - Matej is going to work on Create Object Classes, Add/Edit Expense and language selection.
- Planning next meeting (2 min)
    - Thursday, 29/02/2024, Echo, 19:00
- Feedback round: What went well and what can be improved next time? (1 min)
    - Everything went well, we started to work as a team. We should improve a little bit our communication, because we don't speak so much, but we are getting there.
- Closure (0 min)
