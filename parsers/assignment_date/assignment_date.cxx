#encoding "utf8"
#GRAMMAR_ROOT S      // указываем корневой нетерминал грамматики

Day -> AnyWord<wff=/([1-2]?[0-9])|(3[0-1])/>; // число от 1 до 31
Month -> Noun<kwtype="month">;                // используем слова из статьи "месяц"
YearDescr -> "рік" | "р. ";
Year -> AnyWord<wff=/[1-2]?[0-9]{1,3}/>; // число от 0 до 2999 с возможным "г" или "г." в конце
Year -> Year YearDescr;
AssignmentDateText -> Day Month Year;
AssignmentDate -> AssignmentDateText interp (FactDate.AssignmentDate);

S -> AssignmentDate;