from entity.User import SessionLocal


class UserModel:
    @staticmethod
    def save_user(user):
        db = SessionLocal()
        try:
            db.add(user)
            db.commit()
            return "User saved successfully"
        except Exception as e:
            print(f"Failed to save user. Rolling back. Error: {e}")
            db.rollback()
        finally:
            db.close()
