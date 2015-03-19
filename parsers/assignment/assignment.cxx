#encoding "utf8"
#GRAMMAR_ROOT S      // указываем корневой нетерминал грамматики

ProperName -> Word<h-reg1> interp (Fact.Surname) Word<h-reg1> interp (Fact.Name) Word<h-reg1> interp (Fact.FatherName);
Position -> 'голова' | 'керівник'  | 'заступник';
Institution -> Word+ ','* Word+;
AssignmentText -> ProperName Position interp (Fact.Position) Institution interp (Fact.Institution);

S -> AssignmentText;