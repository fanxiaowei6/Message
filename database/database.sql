/**
*
*Author:
*Data:
*Desc:
*
*/


drop table if exists complain;
/*==============================================================*/

/* Table: complain                                              */
/*==============================================================*/

create table complain 
(
   comp_id              varchar(32)                    not null,
   comp_company         varchar(100)                   null,
   comp_name            varchar(20)                    null,
   comp_moblie          varchar(20)                    null,
   is_NM                smallint                       null,
   comp_time            timestamp                      null,
   comp_title           varchar(200)                   not null,
   to_comp_name         varchar(20)                    null,
   comp_dept            varchar(100)                   null,
   comp_content         long varchar                   null,
   state                varchar(1)                     null,
   constraint PK_COMPLAIN primary key (comp_id)
);


create table complain_reply 
(
   reply_id             varchar(32)                    not null,
   comp_id              varchar(32)                    not null,
   replyer              varchar(20)                    null,
   reply_dept           varchar(100)                   null,
   reply_time           timestamp                      null,
   reply_content        varchar(300)                   null,
   constraint PK_COMPLAIN_REPLY primary key (reply_id)
);

alter table complain_reply
   add constraint FK_COMPLAIN_COMPLAIN__COMPLAIN foreign key (comp_id)
      references complain (comp_id)
      on update restrict
      on delete restrict;

