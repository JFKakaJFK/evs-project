# Features

## 1. User management - FERTIG

- [x] User entity und Service anpassen (@Chris)
- [x] PasswordEncoder einfügen und testUser anpassen (@Chris)
- [x] [addUserController](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/java/at/qe/sepm/skeleton/ui/controllers/AddUserController.java) anpassen (@Markus @Melanie)
- [x] [users.xhtml](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/webapp/admin/users.xhtml) anpassen und [formular](https://git.uibk.ac.at/csat2187/evs-projektarbeit/blob/master/src/main/webapp/admin/create-user.xhtml) einfügen (@Andi)

### TODO/Notes

- NOTE: cNumber Getters und Setters müssen so geschrieben werden weil JSF diese sonst nicht erkennt
- [x] navbar anpassen (brauchen wir die tests?)
- [x] sollen in den views alle ROLES angezeigt werden oder nur die mit am meisten rechte?
- [ ] TODO: zb sicheres passwort generieren und per mail schicken
- [ ] BUG: user overview sortieren geht erst nachdem man in der suchleiste etwas gesucht hat(umstellung der List<> auf filteredUsers)

## 2. Equipment management & Opening Hours - FERTIG

- [x] Equipment Entity und Service (@Johannes)
- [x] testEquipments hinzufügen (@Johannes)
- [x] EquipmentListController (@Melanie @Markus)
- [x] addEquipmentController (@Melanie @Markus)
- [x] view zur Übersicht aller Equipments und equipment hinzufügen **ADMIN** (@Andi)
- [x] OpeningHours Entity und Service (@Chris)
- [x] testÖffnungszeiten hinzufügen (@Chris)
- [x] view zur Einsicht der Öffnungszeiten und zum ändern **ADMIN** (@Andi)
- [x] view zum Ansehen der equipmentDetails(Kommentare, Manuals) **?ALLe? = STUDENT** und Kommentare/Manuals hinzufügen/entfernen nur für **ADMIN**

### TODO/Notes

- [ ] Bug: beim ändern des eqipments wird die maximale ausleihdauer auf 0 gesetzt wenn man diese nicht verändert
- [x] TODO: erstellen von kommentaren
- [ ] Bug: beim erstellen der anleitungen/kommentare wird die tabelle wieder eingeklappt/es sind anfangs immer alle expansions ausgeklappt[so thread](https://stackoverflow.com/questions/43598420/how-to-keep-primefaces-rowexpansion-open-in-an-update-of-the-table)
- [x] TODO: up/download von manuals

## 3. Alle Reservations - IN ENTWICKLUNG

- [x] Reservation Entity (@Johannes)
- [x] testReservationen (@Johannes)
- [x] ReservationListController (@Melanie @Markus)
- [x] addReservationController (@Melanie @Markus)
- [x] Bestätigungsmails (@Markus)
- [x] AddEquipmentGroupReservationController (@Melanie @Markus)
- [x] EquipmentGroupListController (@Melanie @Markus)
- [x] view zur Einsicht, erstellen & löschen der eigenen Reservierungen **EMPLOYEE** (@Chris)
- [ ] view zur Einsicht aller Reservierungen(und löschen) **ADMIN** (@Melanie)
- [x] view zur Einsicht aller ausgeliehenen und überfälligen Reservierungen(und Rckgabebestätigung) **ADMIN** (@Chris)
- [x] view zur Einsicht(und löschen) aller eigenen Reservierungen und reservierung hinzufügen **STUDENT** (@Chris)

### TODO/Notes

- [x] TODO: Kaskadierung beim löschen in alle Richtungen (sollte passen)

## 4. EquipmentGroup management - FERTIG

- [x] equipmentgroup entity (@Johannes)
- [x] testEquipmentGroups (@Johannes)
- [x] EquipmentGroupListController (@Melanie @Markus)
- [x] AddEquipmentGroupController (@Melanie @Markus)
- [x] view zur Einsicht, erstellen & löschen der eigenen EquipmentGroups **EMPLOYEE** (@Andi)
- [x] view zur Einsicht und löschung aller EquipmentGroups **ADMIN** (@Andi)

### TODO/Notes

- [x] TODO: Kaskadierung beim löschen von equipmentgruppen eventuell besser umsetzen

## 6. TODO

- [x] ui sprache ist deutsch, alles was noch englisch ist anpassen (@Melanie)
- [x] bei allen zurück buttons bei denen die form validiert und bei leerem input gewarnt wird, beim zurück button `immediate="true"` attribut setzen. (@Melanie)
- [ ] bei der löschung von entities loggen(siehe equipmentservice) (@Andi)
- [ ] growl/messages für alle delete/add aktionen? (@Andi)
- [x] `emptyMessage=""` bei den datatables anpassen? (@Andi)
- [ ] `scrollable="true" scrollHeight="500"` bei den datatables hinzufügen (@Andi)
- [x] bei den datatables pagination, search & sort?(siehe neue user overview) (@Andi)
- [x] add & delete buttons einfärben (@Johannes)
- [ ] TODOs im code finden und erledigen (@alle mit TODOs)

## Technische Dokumentation

> Fertigen Sie eine Pr¨asentation (max. 10 Minuten) der Technischen Details zu ihrem
System an, die am 21.01.2018 im Proseminar pr¨asentiert werden muss. Hierzu
werden zwei bis drei Studierende ihres Teams zuf¨allig ausgew¨ahlt um die Inhalte
zu pr¨asentieren.

> Gruppe 2
Geräteverwaltung (Rückgabe, Gruppen)
Wie haben Sie das Projekt organisiert (Kommunikation, Branching in GIT, etc.)?


- Feature File-Up/Download (@Johannes)

## Live-Demo

> Bereiten Sie eine Live-Demo (max. 10 Minuten) Ihres Systems vor, welche am
28.01.2018 im Proseminar pr¨asentiert wird. Hierzu werden zwei bis drei Studierende
ihres Teams zuf¨allig ausgew¨ahlt um die Inhalte zu pr¨asentieren.


## Vor Abgabe

- [ ] kontrolle ob alle features laut spezifikation existieren
- [ ] test entities für alles und dokumentation
- [ ] vagrantfile testen und falls nötig ändern
- [ ] unit tests
