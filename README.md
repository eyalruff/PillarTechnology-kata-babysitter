# PillarTechnology-kata-babysitter
Hi - 
This is a Java web application project. in order to run it you can download it and open it as a dynamic web project. I used Tomcat 7.0 to run this project on. (please make sure you run it on a local server otherwise a CORS issue might occur).

- Once you'll run the project the index web page will show

1. First step - You have to choose the date in which you have babysat. otherwise the other input fields will be disabled.
2. Once you have set the date, two input field will be enabled - one for check in date and time and the other for check out date and time.
3. If you'll set wrong dates you will get notify and the data won't be sent to the server.

Test scenarios for wrong dates:
- check in date:
- Earlier than 17 on the date you have choosen. (You'll see an X next to the date field)
- Later than 4 one day after the date you have choosen. (You'll see an X next to the date field)
- invalid date (letters instead of numbers)

- check out date:
- Earlier than 17 on the date you have choosen. (You'll see an X next to the date field)
- Later than 4 one day after the date you have choosen. (You'll see an X next to the date field)
- invalid date (letters instead of numbers)
- before the check in date
- equals to check in date

Once you have entered the dates you can click the "Calculate" button and get your total salary :)


Notes:
- from start-time to bedtime (17:00-20:00) $12/hour
- from bedtime to midnight (20:00-00:00) $8/hour
- from midnight to end of job (00:00-04:00) $16/hour

hours will be rounded like so:
if 0 < minutes <30 rounded to the same hour 19:10 -> 19:00 
if 30 <= minutes <=59 rounded to the next hour 19:58 ->20:00

if after the hours are equal after being rounded, one hour will be added to the check out time so the babysitter will get paid
check in time: 19:40 -> 20:00, check out time: 19:58->20:00.==> check out time: 21:00
- adge case: if both time are equals at 04:00, the checkin time will be the 03:00AM
check in time: 03:31 -> 04:00, check out time: 03:40->04:00.==> check in time: 03:00
