--
-- Copyright © 2013-2021, The SeedStack authors <http://seedstack.org>
--
-- This Source Code Form is subject to the terms of the Mozilla Public
-- License, v. 2.0. If a copy of the MPL was not distributed with this
-- file, You can obtain one at http://mozilla.org/MPL/2.0/.
--

create table tableTest (
	Id int,
	Foo varchar(255)
);
INSERT INTO tableTest (Id, foo) VALUES (1, 'bar');