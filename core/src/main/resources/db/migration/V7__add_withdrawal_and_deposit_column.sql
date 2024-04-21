alter table transfer rename column amount to withdrawal;
alter table transfer add column deposit bigint;

update transfer set withdrawal = -1 * withdrawal;

with tsf as (
    select t.id from transfer t
    join bill target_bill on target_bill.id = t.target_bill_id
    join bill source_bill on source_bill.id = t.source_bill_id
    where target_bill.currency = source_bill.currency
)
update transfer
set deposit = -1 * transfer.withdrawal
from tsf
where transfer.id = tsf.id;