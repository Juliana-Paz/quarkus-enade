insert into Curso (name) values('Direito');
insert into Curso (name) values('Sistemas de Informação');
insert into Curso (name) values('Serviço Social');



insert into Users (name, enrollment, phoneNumber, id_curso) values('Juliana', '093094000233', '63901304814', 1);
insert into Users (name, enrollment, phoneNumber, id_curso) values('Kaio', '092094003452', '63991356856', 2);
insert into Users (name, enrollment, phoneNumber, id_curso) values('Ailana', '456094000931', '63992345678', 1);


-- Resposta de DI

insert into Answer (answerText) values('Valor 1');
insert into Answer (answerText) values('Valor 2');
insert into Answer (answerText) values('Valor 3');
insert into Answer (answerText) values('Valor 4');
insert into Answer (answerText) values('Valor 5');

insert into Answer (answerText) values('Valor 6');
insert into Answer (answerText) values('Valor 7');
insert into Answer (answerText) values('Valor 8');
insert into Answer (answerText) values('Valor 9');
insert into Answer (answerText) values('Valor 10');

insert into Answer (answerText) values('Valor 11');
insert into Answer (answerText) values('Valor 12');
insert into Answer (answerText) values('Valor 13');
insert into Answer (answerText) values('Valor 14');
insert into Answer (answerText) values('Valor 15');


-- Resposta de SI

insert into Answer (answerText) values('Valor 1');
insert into Answer (answerText) values('Valor 2');
insert into Answer (answerText) values('Valor 3');
insert into Answer (answerText) values('Valor 4');
insert into Answer (answerText) values('Valor 5');

insert into Answer (answerText) values('Valor 6');
insert into Answer (answerText) values('Valor 7');
insert into Answer (answerText) values('Valor 8');
insert into Answer (answerText) values('Valor 9');
insert into Answer (answerText) values('Valor 10');

insert into Answer (answerText) values('Valor 11');
insert into Answer (answerText) values('Valor 12');
insert into Answer (answerText) values('Valor 13');
insert into Answer (answerText) values('Valor 14');
insert into Answer (answerText) values('Valor 15');


-- Resposta de SS

insert into Answer (answerText) values('Valor 1');
insert into Answer (answerText) values('Valor 2');
insert into Answer (answerText) values('Valor 3');
insert into Answer (answerText) values('Valor 4');
insert into Answer (answerText) values('Valor 5');

insert into Answer (answerText) values('Valor 6');
insert into Answer (answerText) values('Valor 7');
insert into Answer (answerText) values('Valor 8');
insert into Answer (answerText) values('Valor 9');
insert into Answer (answerText) values('Valor 10');

insert into Answer (answerText) values('Valor 11');
insert into Answer (answerText) values('Valor 12');
insert into Answer (answerText) values('Valor 13');
insert into Answer (answerText) values('Valor 14');
insert into Answer (answerText) values('Valor 15');


-- Questao de DI

insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Direito: 1', 'Explanation1', 1, 1);
insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Direito: 2', 'Explanation2', 8, 1);
insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Direito: 3', 'Explanation3', 14, 1);

-- Questao de SI

insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Sistemas: 1', 'Explanation1', 1, 2);
insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Sistemas: 2', 'Explanation2', 8, 2);
insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Sistemas: 3', 'Explanation3', 14, 2);

-- Questao de SS

insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Servico Social: 1', 'Explanation1', 1, 3);
insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Servico Social: 2', 'Explanation2', 8, 3);
insert into Question (questionText, explanation, id_answer_correct, id_curso) values('Pergunta Servico Social: 3', 'Explanation3', 14, 3);



insert into question_answer (id_question, id_answer) values(1, 1);
insert into question_answer (id_question, id_answer) values(1, 2);
insert into question_answer (id_question, id_answer) values(1, 3);
insert into question_answer (id_question, id_answer) values(1, 4);
insert into question_answer (id_question, id_answer) values(1, 5);

insert into question_answer (id_question, id_answer) values(2, 6);
insert into question_answer (id_question, id_answer) values(2, 7);
insert into question_answer (id_question, id_answer) values(2, 8);
insert into question_answer (id_question, id_answer) values(2, 9);
insert into question_answer (id_question, id_answer) values(2, 10);

insert into question_answer (id_question, id_answer) values(3, 11);
insert into question_answer (id_question, id_answer) values(3, 12);
insert into question_answer (id_question, id_answer) values(3, 13);
insert into question_answer (id_question, id_answer) values(3, 14);
insert into question_answer (id_question, id_answer) values(3, 15);

insert into topic (name) values('Conhecimento Geral');



