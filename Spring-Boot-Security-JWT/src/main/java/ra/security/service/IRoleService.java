package ra.security.service;

import ra.security.model.domain.Role;
import ra.security.model.domain.RoleName;

public interface IRoleService {
    Role findByRoleName(RoleName roleName);
}
