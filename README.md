# Домашнее задание к занятию: Тестирование программы. Mockito

## Задача «Тестирование сервиса медицинских показаний»

### Описание
В репозитории [сервиса медицинских показаний](https://github.com/neee/healthcare-service) находится код приложения для хранения медицинских показаний пациентов клиники
(фамилия, имя, дата рождения, кровяное давление, температура), вся информация записывается в файл (репозиторий).
Наша задача написать/добавить unit-тесты с использованием библиотеки mockito для проверки корректности работы функционала.

### Что нужно сделать
- Написать тесты для проверки класса MedicalServiceImpl, сделав заглушку для класса PatientInfoFileRepository, который он использует
    1. Проверить вывод сообщения во время проверки давления `checkBloodPressure`
    2. Проверить вывод сообщения во время проверки температуры `checkTemperature`
    3. Проверить, что сообщения не выводятся, когда показатели в норме.