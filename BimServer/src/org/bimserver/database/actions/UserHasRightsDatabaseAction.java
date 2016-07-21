package org.bimserver.database.actions;

/******************************************************************************
 * Copyright (C) 2009-2016  BIMserver.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see {@literal<http://www.gnu.org/licenses/>}.
 *****************************************************************************/

import org.bimserver.BimserverDatabaseException;
import org.bimserver.database.BimserverLockConflictException;
import org.bimserver.database.DatabaseSession;
import org.bimserver.models.log.AccessMethod;
import org.bimserver.models.store.Project;
import org.bimserver.models.store.User;
import org.bimserver.shared.exceptions.UserException;
import org.bimserver.webservices.authorization.Authorization;

public class UserHasRightsDatabaseAction extends BimDatabaseAction<Boolean> {

	private final long poid;
	private final long uoid;
	private Authorization authorization;

	public UserHasRightsDatabaseAction(DatabaseSession databaseSession, AccessMethod accessMethod, long uoid, Authorization authorization, long poid) {
		super(databaseSession, accessMethod);
		this.uoid = uoid;
		this.authorization = authorization;
		this.poid = poid;
	}

	@Override
	public Boolean execute() throws UserException, BimserverLockConflictException, BimserverDatabaseException {
		Project project = getProjectByPoid(poid);
		User user = getUserByUoid(uoid);
		return authorization.hasRightsOnProjectOrSuperProjectsOrSubProjects(user, project);
	}
}